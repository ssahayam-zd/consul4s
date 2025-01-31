package consul4s.circe.model

import consul4s.model.CheckStatus
import consul4s.model.agent._
import consul4s.model.health.HealthCheck
import io.circe._
import io.circe.syntax._
import io.circe.Decoder.Result

trait Agent { this: Common with Health =>
  implicit val upstreamDestTypeDecoder: Decoder[UpstreamDestType] = new Decoder[UpstreamDestType] {
    override def apply(c: HCursor): Result[UpstreamDestType] = for {
      value <- c.as[String]
    } yield UpstreamDestType.withValue(value)
  }

  implicit val upstreamDestTypeEncoder: Encoder[UpstreamDestType] = new Encoder[UpstreamDestType] {
    override def apply(a: UpstreamDestType): Json = Json.fromString(a.value)
  }

  implicit val weightsDecoder: Decoder[Weights] = new Decoder[Weights] {
    override def apply(c: HCursor): Result[Weights] = for {
      passing <- c.downField("Passing").as[Int]
      warning <- c.downField("Warning").as[Int]
    } yield Weights(passing, warning)
  }

  implicit val weightsEncoder: Encoder[Weights] = new Encoder[Weights] {
    override def apply(a: Weights): Json = Json.obj(
      ("Passing", a.Passing.asJson),
      ("Warning", a.Warning.asJson)
    )
  }

  implicit val taggedAddressDecoder: Decoder[TaggedAddress] = new Decoder[TaggedAddress] {
    override def apply(c: HCursor): Result[TaggedAddress] = for {
      address <- c.downField("Address").as[String]
      port <- c.downField("Port").as[Int]
    } yield TaggedAddress(address, port)
  }

  implicit val taggedAddressEncoder: Encoder[TaggedAddress] = new Encoder[TaggedAddress] {
    override def apply(a: TaggedAddress): Json = Json.obj(
      ("Address", a.Address.asJson),
      ("Port", a.Port.asJson)
    )
  }

  implicit val scriptCheckEncoder: Encoder[ScriptCheck] = new Encoder[ScriptCheck] {
    override def apply(a: ScriptCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("Args", a.Args.asJson),
      ("Timeout", a.Timeout.asJson),
      ("Interval", a.Interval.asJson),
      ("ID", a.ID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val httpCheckEncoder: Encoder[HttpCheck] = new Encoder[HttpCheck] {
    override def apply(a: HttpCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("HTTP", a.HTTP.asJson),
      ("TLSSkipVerify", a.TLSSkipVerify.asJson),
      ("Interval", a.Interval.asJson),
      ("Timeout", a.Timeout.asJson),
      ("Header", a.Header.asJson),
      ("Method", a.Method.asJson),
      ("Body", a.Body.asJson),
      ("ID", a.ID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val tcpCheckEncoder: Encoder[TCPCheck] = new Encoder[TCPCheck] {
    override def apply(a: TCPCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("TCP", a.TCP.asJson),
      ("Interval", a.Interval.asJson),
      ("Timeout", a.Timeout.asJson),
      ("ID", a.ID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val ttlCheckEncoder: Encoder[TTLCheck] = new Encoder[TTLCheck] {
    override def apply(a: TTLCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("TTL", a.TTL.asJson),
      ("ID", a.ID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val dockerCheckEncoder: Encoder[DockerCheck] = new Encoder[DockerCheck] {
    override def apply(a: DockerCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("DockerContainerID", a.DockerContainerID.asJson),
      ("Shell", a.Shell.asJson),
      ("Args", a.Args.asJson),
      ("Interval", a.Interval.asJson),
      ("ID", a.ID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val gRpcCheckEncoder: Encoder[GrpcCheck] = new Encoder[GrpcCheck] {
    override def apply(a: GrpcCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("GRPC", a.GRPC.asJson),
      ("GRPCUseTLS", a.GRPCUseTLS.asJson),
      ("Interval", a.Interval.asJson),
      ("ID", a.ID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val aliasCheckEncoder: Encoder[AliasCheck] = new Encoder[AliasCheck] {
    override def apply(a: AliasCheck): Json = Json.obj(
      ("ID", a.ID.asJson),
      ("AliasNode", a.AliasNode.asJson),
      ("AliasService", a.AliasService.asJson)
    )
  }

  implicit val checkEncoder: Encoder[Check] = Encoder.instance {
    case v: ScriptCheck => v.asJson
    case v: HttpCheck   => v.asJson
    case v: TCPCheck    => v.asJson
    case v: TTLCheck    => v.asJson
    case v: DockerCheck => v.asJson
    case v: GrpcCheck   => v.asJson
    case v: AliasCheck  => v.asJson
  }

  implicit val serviceScriptCheckEncoder: Encoder[ServiceScriptCheck] = new Encoder[ServiceScriptCheck] {
    override def apply(a: ServiceScriptCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("Args", a.Args.asJson),
      ("Timeout", a.Timeout.asJson),
      ("Interval", a.Interval.asJson),
      ("CheckID", a.CheckID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val serviceHttpCheckEncoder: Encoder[ServiceHttpCheck] = new Encoder[ServiceHttpCheck] {
    override def apply(a: ServiceHttpCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("HTTP", a.HTTP.asJson),
      ("TLSSkipVerify", a.TLSSkipVerify.asJson),
      ("Interval", a.Interval.asJson),
      ("Timeout", a.Timeout.asJson),
      ("Header", a.Header.asJson),
      ("Method", a.Method.asJson),
      ("Body", a.Body.asJson),
      ("CheckID", a.CheckID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val serviceTcpCheckEncoder: Encoder[ServiceTCPCheck] = new Encoder[ServiceTCPCheck] {
    override def apply(a: ServiceTCPCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("TCP", a.TCP.asJson),
      ("Interval", a.Interval.asJson),
      ("Timeout", a.Timeout.asJson),
      ("CheckID", a.CheckID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val serviceTtlCheckEncoder: Encoder[ServiceTTLCheck] = new Encoder[ServiceTTLCheck] {
    override def apply(a: ServiceTTLCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("TTL", a.TTL.asJson),
      ("CheckID", a.CheckID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val serviceDockerCheckEncoder: Encoder[ServiceDockerCheck] = new Encoder[ServiceDockerCheck] {
    override def apply(a: ServiceDockerCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("DockerContainerID", a.DockerContainerID.asJson),
      ("Shell", a.Shell.asJson),
      ("Args", a.Args.asJson),
      ("Interval", a.Interval.asJson),
      ("CheckID", a.CheckID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val serviceGrpcCheckEncoder: Encoder[ServiceGrpcCheck] = new Encoder[ServiceGrpcCheck] {
    override def apply(a: ServiceGrpcCheck): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("GRPC", a.GRPC.asJson),
      ("GRPCUseTLS", a.GRPCUseTLS.asJson),
      ("Interval", a.Interval.asJson),
      ("CheckID", a.CheckID.asJson),
      ("ServiceID", a.ServiceID.asJson),
      ("Status", a.Status.asJson),
      ("Notes", a.Notes.asJson),
      ("SuccessBeforePassing", a.SuccessBeforePassing.asJson),
      ("FailuresBeforeCritical", a.FailuresBeforeCritical.asJson),
      ("DeregisterCriticalServiceAfter", a.DeregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val serviceAliasCheckEncoder: Encoder[ServiceAliasCheck] = new Encoder[ServiceAliasCheck] {
    override def apply(a: ServiceAliasCheck): Json = Json.obj(
      ("CheckID", a.CheckID.asJson),
      ("AliasNode", a.AliasNode.asJson),
      ("AliasService", a.AliasService.asJson)
    )
  }

  implicit val serviceCheckEncoder: Encoder[ServiceCheck] = Encoder.instance {
    case v: ServiceScriptCheck => v.asJson
    case v: ServiceHttpCheck   => v.asJson
    case v: ServiceTCPCheck    => v.asJson
    case v: ServiceTTLCheck    => v.asJson
    case v: ServiceDockerCheck => v.asJson
    case v: ServiceGrpcCheck   => v.asJson
    case v: ServiceAliasCheck  => v.asJson
  }

  implicit val memberInfoDecoder: Decoder[MemberInfo] = new Decoder[MemberInfo] {
    override def apply(c: HCursor): Result[MemberInfo] = for {
      name <- c.downField("Name").as[String]
      addr <- c.downField("Addr").as[String]
      port <- c.downField("Port").as[Int]
      tags <- c.downField("Tags").as[Option[Map[String, String]]]
      status <- c.downField("Status").as[Int]
      protocolMin <- c.downField("ProtocolMin").as[Int]
      protocolMax <- c.downField("ProtocolMax").as[Int]
      protocolCur <- c.downField("ProtocolCur").as[Int]
      delegateMin <- c.downField("DelegateMin").as[Int]
      delegateMax <- c.downField("DelegateMax").as[Int]
      delegateCur <- c.downField("DelegateCur").as[Int]
    } yield MemberInfo(
      name,
      addr,
      port,
      tags,
      status,
      protocolMin,
      protocolMax,
      protocolCur,
      delegateMin,
      delegateMax,
      delegateCur
    )
  }

  implicit val checkUpdateEncoder: Encoder[CheckUpdate] = new Encoder[CheckUpdate] {
    override def apply(a: CheckUpdate): Json = Json.obj(
      ("Status", a.Status.asJson),
      ("Output", a.Output.asJson)
    )
  }

  implicit val serviceDecoder: Decoder[Service] = new Decoder[Service] {
    override def apply(c: HCursor): Result[Service] = for {
      service <- c.downField("Service").as[String]
      id <- c.downField("ID").as[String]
      tags <- c.downField("Tags").as[Option[List[String]]]
      address <- c.downField("Address").as[String]
      taggedAddresses <- c.downField("TaggedAddresses").as[Option[Map[String, TaggedAddress]]]
      meta <- c.downField("Meta").as[Option[Map[String, String]]]
      port <- c.downField("Port").as[Int]
      enableTagOverride <- c.downField("EnableTagOverride").as[Boolean]
      weights <- c.downField("Weights").as[Weights]
    } yield Service(
      service,
      id,
      tags,
      address,
      taggedAddresses,
      meta,
      port,
      enableTagOverride,
      weights
    )
  }

  implicit val newServiceEncoder: Encoder[NewService] = new Encoder[NewService] {
    override def apply(a: NewService): Json = Json.obj(
      ("Name", a.Name.asJson),
      ("ID", a.ID.asJson),
      ("Tags", a.Tags.asJson),
      ("Address", a.Address.asJson),
      ("TaggedAddresses", a.TaggedAddresses.asJson),
      ("Meta", a.Meta.asJson),
      ("Port", a.Port.asJson),
      ("Check", a.Check.asJson),
      ("Checks", a.Checks.asJson),
      ("EnableTagOverride", a.EnableTagOverride.asJson),
      ("Weights", a.Weights.asJson)
    )
  }

  implicit val aggregatedServiceStatusDecoder: Decoder[AggregatedServiceStatus] = new Decoder[AggregatedServiceStatus] {
    override def apply(c: HCursor): Result[AggregatedServiceStatus] = for {
      aggregatedStatus <- c.downField("AggregatedStatus").as[CheckStatus]
      service <- c.downField("Service").as[Service]
      checks <- c.downField("Checks").as[List[HealthCheck]]
    } yield AggregatedServiceStatus(aggregatedStatus, service, checks)
  }

  implicit val tokenEncoder: Encoder[Token] = new Encoder[Token] {
    override def apply(a: Token): Json = Json.obj(
      ("Token", a.Token.asJson)
    )
  }
}
